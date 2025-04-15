package hu.infokristaly.homework4mongodb.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Reduce;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.SortArray;
import org.springframework.data.mongodb.core.aggregation.GraphLookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.StringOperators.Concat;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Let;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Let.ExpressionVariable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.Directory;

public class DirectoryRepoImpl {

    @Autowired
    MongoTemplate mongoTemplate;

    public UpdateResult updateDirectory(Directory directory) {
        Query query = new Query(Criteria.where("id").is(directory.getId()));
        Update update = new Update();
        update.set("path", directory.getPath());
        update.set("parentDirectory", directory.getParentDirectory());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Directory.class);
        return result;
    }

    public String buildDirectoryHierarchy(Directory directory) {
        // 1. $match stage
        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(directory.getId()));

        // 2. $graphLookup stage
        GraphLookupOperation graphLookupStage = Aggregation.graphLookup("directory")
                .startWith("$parentDirectory.$id")
                .connectFrom("parentDirectory.$id")
                .connectTo("_id")
                .depthField("level")
                .as("parents");

        // 3. $addFields stage - Sort the parents array
        Sort sortByLevelDesc = Sort.by(Sort.Direction.DESC, "level");
        SortArray sortArrayParents = SortArray.sortArray("$parents").by(sortByLevelDesc);
        AddFieldsOperation addFieldsStage = Aggregation.addFields().addField("parents").withValue(sortArrayParents).build();

        // 4. $project stage - MODIFIED to calculate and return only fullPath
        Concat reduceConcatExpr = Concat.valueOf("$$value")
                                                        .concat("/")
                                                        .concat("$$this");
        Reduce parentPathReduceExpr = Reduce.arrayOf("$parents.path")
                                                                .withInitialValue("")
                                                                .reduce(reduceConcatExpr);

        ExpressionVariable parentPathVar = ExpressionVariable.newVariable("parentPathCalc").forExpression(parentPathReduceExpr);


        StringOperators.Concat finalConcatExpr = Concat.valueOf("$$parentPathCalc") // Use the variable
                                                        .concat("/")
                                                        .concatValueOf("$path"); // Concat original path of current doc

        VariableOperators.Let letExpr = Let.define(parentPathVar).andApply(finalConcatExpr);

        // --- Build the final $project stage ---
        ProjectionOperation projectStage = Aggregation.project()
                .andExclude("_id") // Exclude the _id field
                .and(letExpr).as("fullPath"); // Calculate using $let and name the result 'fullPath'


        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                graphLookupStage,
                addFieldsStage,
                projectStage 
        );

        AggregationResults<FullPathResult> results = mongoTemplate.aggregate(
                aggregation, "directory", FullPathResult.class
        );

        FullPathResult uniqueResult = results.getUniqueMappedResult();

        return Optional.ofNullable(uniqueResult)
                .map(FullPathResult::getFullPath)
                .orElse(null);
    }

}
