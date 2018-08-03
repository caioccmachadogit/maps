package base.imonitore.com.br.mapsapplication.servicesmanager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GsonEnumAdapter implements JsonSerializer<Enum<?>>, JsonDeserializer<Enum<?>> {

    @Override
    public JsonElement serialize(Enum<?> enumerator, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(enumerator.ordinal());
    }

    @Override
    public Enum<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        //Log.i("GsonEnumAdapter", "deserialize type" + type.toString());

		/*Class<Enum<?>> clazz = (Class<Enum<?>>) type.getClass();
		Enum<?>[] values = clazz.getEnumConstants();*/


//		Enum<?> elements = null;
//
//



//		if(index > 0) index--;
//		else 		  index = 0;
//
//		/*return values[index];*/


//

//        if (type == PerfilUserEnum.class) {
//            int index = json.getAsInt() - 1;
//            return PerfilUserEnum.values()[index];
//        }
//        if (type == FormatoMidiaEnum.class)
//            return FormatoMidiaEnum.values()[json.getAsInt()];
//
//        if (type == DigitalEnum.class)
//            return DigitalEnum.values()[json.getAsInt()];

//		if (type == TipoMidiaEnum.class)
//			return  TipoMidiaEnum.values()[index];

//		if (type == CategoriaCNHEnum.class)
//			return  CategoriaCNHEnum.values()[index];
//
//		if (type == TipoAlertaEnum.class)
//			return  TipoAlertaEnum.values()[index];
//
//		if (type == StatusAgendaEnum.class)
//			return  StatusAgendaEnum.values()[index];
//
//		if (type == GravidadeInfracaoEnum.class)
//			return  GravidadeInfracaoEnum.values()[index];
//
//		if (type == DigitalEnum.class){
//			elements = DigitalEnum.values()[index];
////			Log.i("Biometria","Nome : "+elements.name()+"Ordem:"+elements.ordinal());
//			return  elements;
//		}
//
//		if (type == TelemetriaEnum.class)
//			return  TelemetriaEnum.values()[index];
//
//		if (type == TipoViaEnum.class)
//			return  TipoViaEnum.values()[index];
//
//        if (type == TipoOcorrenciaEnum.class)
//            return  TipoOcorrenciaEnum.values()[index];
//
//        if (type == TipoBalizaEnum.class)
//            return  TipoBalizaEnum.values()[index];
//
//		if (type == StatusAprovacaoEnum.class)
//			return  StatusAprovacaoEnum.values()[index];
//
//
//		if (type == StatusMidia.class)
//			return  StatusMidia.values()[index];
//
//		if (type == FaltasEnum.class)
//			return  FaltasEnum.values()[index];

        return null;
    }
}
