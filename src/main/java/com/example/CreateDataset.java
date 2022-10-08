/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bigquery;

// [START bigquery_create_dataset]
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.TableDefinition;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;

public class CreateDataset {

  public static void main(String[] args) {
    String datasetName = "dataflow_demo_dataset";
    String location = "US";
    createDataset(datasetName,location);
    String tableName = "Customer";
    Schema schema =
		        Schema.of(
		            Field.of("customername", StandardSQLTypeName.STRING),
		            Field.of("customerid", StandardSQLTypeName.NUMERIC),
		            Field.of("isactive", StandardSQLTypeName.BOOL));
		createTable(datasetName, tableName, schema);
  }

  public static String createDataset(String datasetName, String location) {
	    	System.out.println("Calling createDataset method....");
			String newDatasetName=null;
	    try {
	      // Initialize client that will be used to send requests. This client only needs to be created
	      // once, and can be reused for multiple requests.
	      BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
	      DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).setLocation(location).build();
	      Dataset newDataset = bigquery.create(datasetInfo);
	      newDatasetName = newDataset.getDatasetId().getDataset();
	      System.out.println(newDatasetName + " created successfully");
	      return newDatasetName;
	    } catch (BigQueryException e) {
	      System.out.println("Dataset was not created. \n" + e.toString());
	    }
	    System.out.println("End createDataset method....");
	    return newDatasetName;
	   
	}
	    
  public static void createTable(String datasetName, String tableName, Schema schema) {
      System.out.println("Calling createTable method....");
      try {
          // Initialize client that will be used to send requests. This client only needs to be created
          // once, and can be reused for multiple requests.
          BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

          TableId tableId = TableId.of(datasetName, tableName);
          TableDefinition tableDefinition = StandardTableDefinition.of(schema);
          TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();

          bigquery.create(tableInfo);
          System.out.println("Table created successfully");
        } catch (BigQueryException e) {
          System.out.println("Table was not created. \n" + e.toString());
        }
      System.out.println("End createTable method....");
    }
    
}
// [END bigquery_create_dataset]
