/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2021 Dmitry Degrave
 * Copyright (c) 2017-2021 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.beacon.rest;

import au.org.garvan.vsal.beacon.entity.BeaconResponseSSVS;
import au.org.garvan.vsal.beacon.entity.Query;
import au.org.garvan.vsal.core.entity.DatasetID;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SSVSCalls {

    private static Map<DatasetID, String> beaconURLs =
        new HashMap<DatasetID, String>() {{
            put(DatasetID.MITO, "http://localhost:7081/ssvs2/core/search");
            put(DatasetID.MGRB, "http://localhost:7082/ssvs2/core/search");
            put(DatasetID.AC, "http://localhost:7083/ssvs2/core/search");
            put(DatasetID.DEMO, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.BM, "http://localhost:7085/ssvs2/core/search");
            put(DatasetID.EE, "http://localhost:7086/ssvs2/core/search");
            put(DatasetID.LD, "http://localhost:7087/ssvs2/core/search");
            put(DatasetID.CIRCA, "http://localhost:7088/ssvs2/core/search");
            put(DatasetID.ACPRO, "http://localhost:7089/ssvs2/core/search");
            put(DatasetID.ICCON, "http://localhost:7090/ssvs2/core/search");
            put(DatasetID.NEURO, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.CHILDRANZ, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.CARDIAC, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.GI, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.HIDDEN, "http://localhost:7084/ssvs2/core/search");
            put(DatasetID.KIDGEN, "http://localhost:7091/ssvs2/core/search");
            put(DatasetID.ASPREE, "http://localhost:7084/ssvs2/core/search");
        }};
    private static Properties prop = null;
    private static String propFileName = "vsal.properties";

    private static ClientResponse restGetCall(String url, MultivaluedMap<String,String> params)
            throws IOException {
//        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
//        } catch (Exception e) {
//            throw new IOException("REST call exception");
//        }
    }

    private static synchronized void readConfig()
            throws IOException {
        if (prop == null) {
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName)) {
                Properties p = new Properties();
                if (inputStream != null) {
                    p.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found.");
                }
                prop = p;
                if (prop.getProperty("beacon.url.mgrb") != null)
                    beaconURLs.put(DatasetID.MGRB, prop.getProperty("beacon.url.mgrb"));
                if (prop.getProperty("beacon.url.mito") != null)
                    beaconURLs.put(DatasetID.MITO, prop.getProperty("beacon.url.mito"));
                if (prop.getProperty("beacon.url.ac") != null)
                    beaconURLs.put(DatasetID.AC, prop.getProperty("beacon.url.ac"));
                if (prop.getProperty("beacon.url.acpro") != null)
                    beaconURLs.put(DatasetID.ACPRO, prop.getProperty("beacon.url.acpro"));
                if (prop.getProperty("beacon.url.demo") != null)
                    beaconURLs.put(DatasetID.DEMO, prop.getProperty("beacon.url.demo"));
                if (prop.getProperty("beacon.url.bm") != null)
                    beaconURLs.put(DatasetID.BM, prop.getProperty("beacon.url.bm"));
                if (prop.getProperty("beacon.url.ee") != null)
                    beaconURLs.put(DatasetID.EE, prop.getProperty("beacon.url.ee"));
                if (prop.getProperty("beacon.url.ld") != null)
                    beaconURLs.put(DatasetID.LD, prop.getProperty("beacon.url.ld"));
                if (prop.getProperty("beacon.url.circa") != null)
                    beaconURLs.put(DatasetID.CIRCA, prop.getProperty("beacon.url.circa"));
                if (prop.getProperty("beacon.url.iccon") != null)
                    beaconURLs.put(DatasetID.ICCON, prop.getProperty("beacon.url.iccon"));
                if (prop.getProperty("beacon.url.kidgen") != null)
                    beaconURLs.put(DatasetID.KIDGEN, prop.getProperty("beacon.url.kidgen"));
            }
        }
    }

    public static BeaconResponseSSVS beacon(Query q) throws IOException {
        if (prop == null) readConfig();
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();

        if (q.getChromosome() != null)
            queryParams.add("chr", q.getChromosome().toString());
        if (q.getPosition() != null)
            queryParams.add("start", q.getPosition().toString()); // convert 0-based beacon protocol into 1-based VCF position
        if (q.getPosition() != null)
            queryParams.add("end", q.getPosition().toString());
        if (q.getAllele() != null)
            queryParams.add("alt", q.getAllele());
        queryParams.add("beacon", "true");
        queryParams.add("dataset", q.getDatasetId());

        ClientResponse queryResult = restGetCall(beaconURLs.get(DatasetID.fromString(q.getDatasetId())), queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        // JAXB Unmarshalling - works fine here
        return queryResult.getEntity(BeaconResponseSSVS.class);
    }
}