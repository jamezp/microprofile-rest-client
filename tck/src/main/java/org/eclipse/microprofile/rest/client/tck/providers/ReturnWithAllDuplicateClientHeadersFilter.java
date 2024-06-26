/*
 * Copyright 2018, 2021 Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.microprofile.rest.client.tck.providers;

import java.io.IOException;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class ReturnWithAllDuplicateClientHeadersFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        JsonObjectBuilder allClientHeaders = Json.createObjectBuilder();
        MultivaluedMap<String, Object> clientHeaders = clientRequestContext.getHeaders();
        for (String headerName : clientHeaders.keySet()) {
            List<Object> header = clientHeaders.get(headerName);
            final JsonArrayBuilder headerValues = Json.createArrayBuilder();
            header.forEach(h -> headerValues.add(h.toString()));
            allClientHeaders.add(headerName, headerValues);
        }
        clientRequestContext.abortWith(Response.ok(allClientHeaders.build()).build());
    }

}
