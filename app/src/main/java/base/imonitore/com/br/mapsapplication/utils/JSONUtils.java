package base.imonitore.com.br.mapsapplication.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import base.imonitore.com.br.mapsapplication.servicesmanager.domain.PerfilUserEnum;
import base.imonitore.com.br.mapsapplication.servicesmanager.GsonDateAdapter;
import base.imonitore.com.br.mapsapplication.servicesmanager.GsonEnumAdapter;

public class JSONUtils {

	private static GsonBuilder builder;
	
	static{
		builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new GsonDateAdapter());
		builder.registerTypeAdapter(PerfilUserEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(StatusExameEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(EtapaExameEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(FormatoMidiaEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(TipoMidiaEnum.class, new GsonEnumAdapter());
//        builder.registerTypeAdapter(StatusAprovacaoExameEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(DigitalEnum.class, new GsonEnumAdapter());
//		builder.registerTypeAdapter(StatusMidiaEnum.class, new GsonEnumAdapter());
	}
	
	public static Gson newGson(){
		return builder.create();
	}
}
