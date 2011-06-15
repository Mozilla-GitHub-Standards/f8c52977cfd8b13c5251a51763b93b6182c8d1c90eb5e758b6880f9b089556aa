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
package com.mozilla.grouperfish.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.mozilla.bagheera.rest.ResourceBase;

@Path("/clusters")
public class ClusterResource extends ResourceBase {

	public ClusterResource() throws IOException {
		super();
	}

	@GET
	@Path("{name}/{key}")
	public String getCluster(@PathParam("name") String name, @PathParam("key") String key) {
		return "TBD";
	}
	
	@GET
	@Path("{name}/{key}/{label}")
	public String getClusters(@PathParam("name") String name, @PathParam("key") String key, @PathParam("label") String label) {
		return "TBD";
	}
	
}