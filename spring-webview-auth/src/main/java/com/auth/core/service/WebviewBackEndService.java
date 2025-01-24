package com.auth.core.service;

import com.auth.core.config.ServiceCacheConfig;
import com.auth.core.domain.constant.ErrorCode;
import com.auth.core.domain.dto.RequestDataChartDTO;
import com.auth.core.domain.dto.ResponseApiDTO;
import com.auth.core.domain.dto.ResponseDataChartDTO;
import com.auth.core.utils.FolderUtils;
import com.auth.core.utils.GsonUtils;
import com.auth.ui.feign.CloudFeignClient;
import com.auth.ui.feign.TransFeignClient;
import com.auth.ui.feign.WebCoreFeignClient;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebviewBackEndService {

    private final TransFeignClient transFeignClient;

    private final CloudFeignClient cloudFeignClient;

    private final WebCoreFeignClient webCoreFeignClient;

    private final ServiceCacheConfig serviceCacheConfig;

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();


    //    ******************************************************************************************************************

    private String getResponseCode(String inputJson) {
        try {
            JsonObject object = (JsonObject) parser.parse(inputJson);// response will be the json String
            return object.get("responseCode").getAsString();
        } catch (Exception e) {
            return null;
        }

    }


    private String convertJsonBigNumb(String inputJson) {
        JsonObject object = (JsonObject) parser.parse(inputJson);// response will be the json String

        JsonArray listData = new JsonArray();
        JsonElement listTemp = object.get("data");

        if (listTemp != null) {
            try {
                JsonArray jsonArray = listTemp.getAsJsonArray();
                for (JsonElement elem : jsonArray) {
                    JsonObject elemTemp = ((JsonObject) elem);
                    Set<String> listkey = new TreeSet<>(elemTemp.keySet());
                    for (String key : listkey) {
                        if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount")) {
                            try {
                                elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                            } catch (Exception ignored) {
                            }
                        } else {
                            try {
                                elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    listData.add(elemTemp);
                }
                object.add("data", listData);
            } catch (Exception e) {
                JsonObject elemTemp = listTemp.getAsJsonObject();
                Set<String> listkey = new TreeSet<>(elemTemp.keySet());
                for (String key : listkey) {
                    if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount")) {
                        try {
                            elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                        } catch (Exception ignored) {
                        }
                    } else {
                        try {
                            elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        return gson.toJson(object);
    }

    public String convertJsonBigNumb2(String inputJson) {
        JsonObject object = (JsonObject) parser.parse(inputJson);// response will be the json String

        JsonArray listData = new JsonArray();
        JsonElement listTemp = object.get("data");

        try {
            JsonArray jsonArray = listTemp.getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                try {
                    JsonObject elemTemp = ((JsonObject) elem);
                    Set<String> listkey = new TreeSet<>(elemTemp.keySet());
                    for (String key : listkey) {
                        if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount")) {
                            elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                        } else {
                            elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                        }
                    }
                    listData.add(elemTemp);
                } catch (Exception e) {
                    try {
                        JsonPrimitive elemTemp = ((JsonPrimitive) elem);
                        listData.add(elemTemp);
                    } catch (Exception e2) {
                        listData.add(elem.getAsJsonArray());
                    }

                }


            }
            object.add("data", listData);
        } catch (Exception e) {
            JsonObject elemTemp = listTemp.getAsJsonObject();
            Set<String> listkey = new TreeSet<>(elemTemp.keySet());
            for (String key : listkey) {
                if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount")) {
                    elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                } else {
                    elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                }
            }
        }

        return gson.toJson(object);
    }

    public String convertJsonBigNumbTM(String inputJson) {
//        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(inputJson);// response will be the json String

        JsonArray listData = new JsonArray();
        JsonElement listTemp = object.get("data");

        if (listTemp != null) {
            try {
                JsonArray jsonArray = listTemp.getAsJsonArray();
                for (JsonElement elem : jsonArray) {
                    try {
                        JsonObject elemTemp = ((JsonObject) elem);
                        Set<String> listkey = new TreeSet<>(elemTemp.keySet());
                        for (String key : listkey) {
                            if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount") || key.toLowerCase().contains("transactionId")) {
                                try {
                                    elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                                } catch (Exception ignored) {
                                }
                            } else {
                                try {
                                    elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                                } catch (Exception ignored) {
                                }
                            }
                        }
                        listData.add(elemTemp);
                    } catch (Exception e) {
                        try {
                            JsonPrimitive elemTemp = ((JsonPrimitive) elem);
                            listData.add(elemTemp);
                        } catch (Exception e2) {
                            listData.add(elem.getAsJsonArray());
                        }

                    }
                }
                object.add("data", listData);
            } catch (Exception e) {
                JsonObject elemTemp = listTemp.getAsJsonObject();
                Set<String> listkey = new TreeSet<>(elemTemp.keySet());
                for (String key : listkey) {
                    if (key.toLowerCase().contains("balance") || key.toLowerCase().contains("amount")) {
                        elemTemp.addProperty(key, elemTemp.get(key).getAsBigDecimal());
                    } else {
                        elemTemp.addProperty(key, elemTemp.get(key).getAsString());
                    }
                }
            }
        }

        return gson.toJson(object);
    }

    //    ******************************************************************************************************************
    public Object callApiGetPhoneBy() {
        Object response = null;
        Object response02 = null;
        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.setBasicAuth("admin", "123123a@");
            response = cloudFeignClient.getPhoneBy(headers, "123");
//            response02 = transFeignClient.getPhoneBy(headers, "123");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return response;
    }

    public Object pingWebCore() {
        Object response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.setBasicAuth("admin", "123123a@");
            response = webCoreFeignClient.pingAvailable(headers);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return response;
    }

    public String callApiExecute(String sqlString) {
        String UrlPath = serviceCacheConfig.getUrlApiPath();
        String url = UrlPath + "/api/v1/public/accounting-log/postQuerySql";
        log.info("URL : " + url + ", SQL : " + sqlString);


        String response = "{}";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("admin", "123123a@");
            response = transFeignClient.callApiExecute02(headers, sqlString);

            int length = 200;
            if (response.length() < 200) {
                length = response.length() - 1;
            }
            log.info("URL : " + url + ", Response : " + response.substring(0, length) + " ... ");
//            response = convertJsonBigNumb(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return response;
    }


    public String getDataAccTable(String numbRecord, String nameRecord) {
        RequestDataChartDTO requestDataChartDTO = new RequestDataChartDTO();
        requestDataChartDTO.setNumbRecord(Integer.valueOf(numbRecord));
        requestDataChartDTO.setNameRecord(nameRecord);


        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "123123a@");

        LinkedHashMap responseApi1 = (LinkedHashMap) webCoreFeignClient.getDataAccTable(headers, requestDataChartDTO);
        List<ResponseDataChartDTO> responseApi = (List<ResponseDataChartDTO>) responseApi1.get("data");
        return GsonUtils.convertObjectToStringJson(responseApi);
    }

    public String getTransChart(String fromDate, String toDate) {
        String UrlPath = serviceCacheConfig.getUrlApiPath();
        String url = UrlPath + "/api/v1/public/accounting-log/getTransChart";

        String response = "{}";
        try {
            response = webCoreFeignClient.getTransChart(fromDate, toDate);
            int length = 200;
            if (response.length() < 200) {
                length = response.length() - 1;
            }
            log.info("URL : " + url + ", Response : " + response.substring(0, length) + " ... ");
            response = convertJsonBigNumb(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public String getTpsChart(String fromDate, String toDate, Integer numbMax) {
        String UrlPath = serviceCacheConfig.getUrlApiPath();
        String url = UrlPath + "/api/v1/public/accounting-log/getTpsChart";

        String response = "{}";
        try {
            ResponseApiDTO responseApiDTO = new ResponseApiDTO();
            responseApiDTO.setData(webCoreFeignClient.getTpsChart(fromDate, toDate).getData());

            responseApiDTO.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
            responseApiDTO.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());
            response = GsonUtils.convertObjectToStringJson(responseApiDTO);
            int length = 200;
            if (response.length() < 200) {
                length = response.length() - 1;
            }
            log.info("URL : " + url + ", Response : " + response.substring(0, length) + " ... ");
//            response = convertJsonBigNumb(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public String getStatusChart(String fromDate, String toDate) {
        String UrlPath = serviceCacheConfig.getUrlApiPath();
        String url = UrlPath + "/api/v1/public/accounting-log/getStatusChart";

        String response = "{}";
        try {
            ResponseApiDTO responseApiDTO = new ResponseApiDTO();
            responseApiDTO.setData(webCoreFeignClient.getStatusChart(fromDate, toDate).getData());

            responseApiDTO.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
            responseApiDTO.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());
            response = GsonUtils.convertObjectToStringJson(responseApiDTO);
            int length = 200;
            if (response.length() < 200) {
                length = response.length() - 1;
            }
            log.info("URL : " + url + ", Response : " + response.substring(0, length) + " ... ");
//            response = convertJsonBigNumb(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public String saveFile(MultipartFile file) {
        String response = "";
        try {
            response = FolderUtils.saveFile(file, serviceCacheConfig.getDestinationFolderSaveFile());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = e.getMessage();
        }
        return response;
    }
}
