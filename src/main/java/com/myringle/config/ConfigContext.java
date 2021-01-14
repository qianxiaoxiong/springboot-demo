package com.myringle.config;

import javax.naming.*;
import javax.naming.spi.NamingManager;
import java.util.*;


public  class ConfigContext implements Context {

    //private Map<String, Config> bindings = new ConcurrentHashMap<String, Config>();
    protected static final NameParser PARSER = new NameParserImpl();

    private Hashtable environment = new Hashtable();
    protected static final String SCHEMA = "config:";
    static class NameParserImpl implements NameParser {
        public Name parse(String name) throws NamingException {
            return new CompositeName(name);
        }
    }

    private SortedMap<String,Config> bindings = new TreeMap<String, Config>();

    private String prefix = "";

    public ConfigContext(){}
    public ConfigContext(Hashtable environment){
        this.environment = environment;
    }

    protected ConfigContext(String prefix){
        this.prefix = prefix;
    }

    protected ConfigContext(String prefix,SortedMap<String,Config> bindings){
        this.prefix = prefix;
        this.bindings = bindings;
    }

    public Object lookup(Name name) throws NamingException {
        return lookup(name.toString()) ;
    }


    public Object lookup(String name) throws NamingException {
        String currentPath = null;
        if(!name.startsWith("/")){
            currentPath = prefix +  "/" + name;
        }  else{
            currentPath = prefix + name;
        }
        Config config = bindings.get(currentPath);
        //如果节点存在,则直接返回
        if(config != null){
                return config;
        }
        SortedMap<String,Config> tailMap = bindings.tailMap(currentPath);
        if(!tailMap.isEmpty()){
            //copy
            SortedMap<String,Config> subBindings = new TreeMap<String, Config>();
            Iterator<String> it = tailMap.keySet().iterator();
            for(Map.Entry<String,Config> entry : tailMap.entrySet()){
                String path = entry.getKey();
                if(path.startsWith(currentPath)){
                    subBindings.put(path,entry.getValue()) ;
                }
            }
            if(!subBindings.isEmpty()){
                return new ConfigContext(currentPath,subBindings);
            }
        }
        //other ,proxy
        int pos = name.indexOf(':');
        if (pos > 0) {
            String scheme = name.substring(0, pos);
            Context ctx = NamingManager.getURLContext(scheme, environment);
            if (ctx != null) {
                return ctx.lookup(name);
            }
        }
        return null;
    }

    public void bind(Name name, Object obj) throws NamingException {
         bind(name.toString(),obj);
    }

    public void bind(String name, Object obj) throws NamingException {
        if(!(obj instanceof Config)){
           return;
        }
        String currentPath = null;
        if(!name.startsWith("/")){
            currentPath = prefix +  "/" + name;
        }  else{
            currentPath = prefix + name;
        }
        bindings.put(currentPath,(Config)obj);
    }

    public void rebind(Name name, Object obj) throws NamingException {
        bind(name,obj);
    }

    public void rebind(String name, Object obj) throws NamingException {
        bind(name,obj);
    }

    public void unbind(Name name) throws NamingException {
        unbind(name.toString());
    }

    public void unbind(String name) throws NamingException {
        bindings.remove(name);
    }

    public void rename(Name oldName, Name newName) throws NamingException {
           rename(oldName.toString(), newName.toString());
    }

    public void rename(String oldName, String newName) throws NamingException {
        if(!bindings.containsKey(oldName)){
            throw new NamingException("Name of " + oldName +" don't exist") ;
        }
        if(bindings.containsKey(newName)){
            throw new NamingException("Name of " + newName + " has already exist.");
        }
        Config value = bindings.remove(oldName);
        bindings.put(newName,value);
    }

    public NameParser getNameParser(String name) throws NamingException {
        return PARSER;
    }

    public Name composeName(Name name, Name prefix) throws NamingException {
        Name result = (Name)prefix.clone();
        result.addAll(name);
        return result;
    }

    public String composeName(String name, String prefix) throws NamingException {
        CompositeName result = new CompositeName(prefix);
        result.addAll(new CompositeName(name));
        return result.toString();
    }

    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return this.environment.put(propName,propName.toString());
    }

    public Object removeFromEnvironment(String propName) throws NamingException {
        return this.environment.remove(propName);
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    @Override
    public void close() throws NamingException {

    }


    public String getNameInNamespace() throws NamingException {
        return "";
    }









    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String name) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

}
