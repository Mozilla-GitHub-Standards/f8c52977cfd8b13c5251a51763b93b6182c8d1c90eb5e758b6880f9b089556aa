/*
 * Copyright 2011 Mozilla Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mozilla.grouperfish.transforms.coclustering.pig.eval.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;

import com.mozilla.util.TextUtil;

public class UnigramExtractor extends EvalFunc<DataBag> {

    private static BagFactory bagFactory = BagFactory.getInstance();
    private static TupleFactory tupleFactory = TupleFactory.getInstance();

    private String stopwordDictPath;
    private Set<String> stopwords;

    public UnigramExtractor() {
        stopwordDictPath = "stopwords.txt";
    }

    private void loadStopwordDict() throws IOException {
        if (stopwordDictPath != null) {
            stopwords = new HashSet<String>();

            FileSystem hdfs = null;
            Path p = new Path(stopwordDictPath);
            hdfs = FileSystem.get(p.toUri(), new Configuration());
            for (FileStatus status : hdfs.listStatus(p)) {
                if (!status.isDir()) {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(hdfs.open(status.getPath())));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            stopwords.add(line.trim());
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }
                }
            }

            log.info("Loaded stopword dictionary with size: " + stopwords.size());
        }
    }

    public DataBag exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }

        if (input.size() > 1) {
            stopwordDictPath = (String) input.get(1);
        }

        if (stopwords == null) {
            loadStopwordDict();
        }

        String normStr = ((String) input.get(0));
        if (normStr == null) {
            return null;
        }

        // Normalize the text
        normStr = TextUtil.normalize(normStr, true);

        DataBag output = bagFactory.newDefaultBag();
        for (String s : TextUtil.tokenize(normStr)) {
            if (s.length() <= 30 && !stopwords.contains(s)) {
                Tuple t = tupleFactory.newTuple(1);
                t.set(0, s);
                output.add(t);
            }
        }

        return output;
    }
}
