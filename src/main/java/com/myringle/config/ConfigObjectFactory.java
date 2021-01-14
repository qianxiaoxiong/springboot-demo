package com.myringle.config;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.util.*;


public class ConfigObjectFactory implements ObjectFactory {

    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
//        // you should be very careful;
//        if (nameCtx != null && name != null) {
//            Object result = nameCtx.lookup(name);
//            if (result != null && (result instanceof Config)) {
//                return result;
//            }
//        }
//        if (name != null && environment != null) {
//            Context context = new InitialContext(environment);
//            Object result = context.lookup(name);
//            if (result != null && (result instanceof Config)) {
//                return result;
//            }
//        }
        //rebuild object from reference
        //
        if (!(obj instanceof Reference)) {
            return null;
        }
        Reference reference = (Reference) obj;
        //类型检测
        if (!Config.class.getName().equalsIgnoreCase(reference.getClassName())) {
            return null;
        }
        Map<String, String> properties = new HashMap<String, String>();
        for (String property : Config.properties) {
            StringRefAddr addr = (StringRefAddr) reference.get(property);
            if (addr != null) {
                properties.put(property, addr.getContent().toString());
            }
        }
        //build
        Config config = new Config();
        config.setName(properties.get("name"));
        config.setSources(properties.get("sources"));
        return config;
    }

    public static void main(String[] args) throws Exception {
        Reference reference = new Reference(Config.class.getName(), ConfigObjectFactory.class.getName(), null);
        reference.add(new StringRefAddr("name", "mysql-jdbc"));
        reference.add(new StringRefAddr("sources", "8.129.18.200:3306"));
        Config config = (Config) new ConfigObjectFactory().getObjectInstance(reference, null, null, null);
        System.out.println(config.getName() + "<>" + config.getSources());
    }
}
