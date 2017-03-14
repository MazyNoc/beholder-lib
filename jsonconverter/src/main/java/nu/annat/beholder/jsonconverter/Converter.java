package nu.annat.beholder.jsonconverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.annat.beholder.ComponentFactory;
import nu.annat.beholder.presenter.ComponentInfo;

public class Converter {

	private final ComponentFactory factory;
	private Map<String, Class<? extends ComponentInfo>> map = new HashMap<>();

	public static class Info {
		public JsonElement data;
		String component;
	}

	public static class InfoList extends ArrayList<Info> {
	}

	public Converter(ComponentFactory factory) {
		this.factory = factory;
		prepare();
	}

	public void prepare() {
		map.clear();
		Collection<Class<? extends ComponentInfo>> infos = factory.getRegisteredPresenters();
		for (Class<? extends ComponentInfo> info : infos) {
			PresenterInfo annotation = info.getAnnotation(PresenterInfo.class);
			if (annotation != null) {
				String name = annotation.name().isEmpty() ? info.getSimpleName() : annotation.name();
				register(name, info);
			}
		}
	}

	private void register(String name, Class<? extends ComponentInfo> info) {
		map.put(name, info);
	}

	public List<ComponentInfo> convert(String json) {
		List<ComponentInfo> result = new ArrayList<>();
		Gson gson = new Gson();
		InfoList infoList = gson.fromJson(json, InfoList.class);
		for (Info info : infoList) {
			Class<? extends ComponentInfo> aClass = map.get(info.component);
			result.add(gson.fromJson(info.data, aClass));
		}
		return result;
	}
}
