package base.imonitore.com.br.googledirection;

import base.imonitore.com.br.googledirection.request.DirectionOriginRequest;

public class GoogleDirection {
    public static DirectionOriginRequest withServerKey(String apiKey) {
        return new DirectionOriginRequest(apiKey);
    }
}
