package tools;

import java.io.IOException;
import java.text.ParseException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.aliyuncs.ccc.model.v20170705.GenerateAgentStatisticReportRequest;
import com.aliyuncs.ccc.model.v20170705.ListCallDetailRecordsRequest;
import com.aliyuncs.ccc.model.v20170705.ListRecordingsByContactIdRequest;
import com.aliyuncs.ccc.model.v20170705.ListRecordingsRequest;
import com.aliyuncs.ccc.model.v20170705.StartBack2BackCallRequest;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;

/**
 * @author edward
 * @date 2018/5/18
 */
public class OpenClientTest extends AbstractTest {
    @Test
    public void testListCallDetailRecords() throws IOException, ParseException, ClientException {
        String instance = Environment.get("instance");
        ListCallDetailRecordsRequest request = new ListCallDetailRecordsRequest();
        request.setInstanceId(instance);
        request.setPageSize(100);
        request.setPageNumber(1);
        request.setStopTime(System.currentTimeMillis());
        request.setStartTime(request.getStopTime() - 1000 * 3600 * 24 * 5);
        if (TokenPackage.TOKEN_TYPE_STS.equals(getTokenPackage().getTokenType())) {
            String result = OpenClient.invokeByStsToken(getTokenPackage().getCredentials(), "ListCallDetailRecords",
                JSON.toJSONString(request));
            System.out.println("**ListCallDetailRecords**");
            System.out.println(result);
        }
    }

    @Test
    public void testListRecordings() throws IOException, ParseException, ClientException {
        String instance = Environment.get("instance");
        ListRecordingsRequest request = new ListRecordingsRequest();
        request.setInstanceId(instance);
        request.setPageSize(100);
        request.setPageNumber(1);
        request.setStopTime(System.currentTimeMillis());
        request.setStartTime(request.getStopTime() - 1000 * 3600 * 24 * 5);
        if (TokenPackage.TOKEN_TYPE_STS.equals(getTokenPackage().getTokenType())) {
            String result = OpenClient.invokeByStsToken(getTokenPackage().getCredentials(), "ListRecordings",
                JSON.toJSONString(request));
            System.out.println("**ListRecordings**");
            System.out.println(result);
        }
    }

    @Test
    public void testStartBack2BackCall() throws IOException, ParseException, ClientException {
        String instance = Environment.get("instance");
        StartBack2BackCallRequest request = new StartBack2BackCallRequest();
        request.setInstanceId(instance);
        request.setCallCenterNumber("01086396869");
        request.setCaller("13501215646");
        request.setCallee("18515653810");
        request.setWorkflowId(null);
        if (TokenPackage.TOKEN_TYPE_STS.equals(getTokenPackage().getTokenType())) {
            String result = OpenClient.invokeByStsToken(getTokenPackage().getCredentials(),
                "StartBack2BackCall",
                JSON.toJSONString(request));
            System.out.println("**StartBack2BackCall**");
            System.out.println(result);
        }
    }

    @Test
    public void testGenerateAgentStatisticReport() throws IOException, ParseException, ClientException {
        String instance = Environment.get("instance");
        GenerateAgentStatisticReportRequest request = new GenerateAgentStatisticReportRequest();
        request.setInstanceId(instance);
        request.setStartDate("2018-06-05");
        request.setEndDate("2018-06-06");
        request.setPageNumber(1);
        request.setPageSize(100);
        if (TokenPackage.TOKEN_TYPE_STS.equals(getTokenPackage().getTokenType())) {
            String result = OpenClient.invokeByStsToken(getTokenPackage().getCredentials(),
                "GenerateAgentStatisticReport",
                JSON.toJSONString(request));
            System.out.println("**GenerateAgentStatisticReport**");
            JSONObject json = JSON.parseObject(result);
            System.out.println(json.get("data").toString());
        }
    }
}
