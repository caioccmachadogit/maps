package base.imonitore.com.br.googledirection;

import base.imonitore.com.br.googledirection.model.Direction;

public interface DirectionCallback {
    void onDirectionSuccess(Direction direction, String rawBody);
    void onDirectionFailure(Throwable t);
}
