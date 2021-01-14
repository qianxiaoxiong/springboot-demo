package com.myringle.config;


import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Config implements Referenceable, Serializable {

    private String name;
    private String sources;
    //配置文件中允许配置的"属性"
    protected static Set<String> properties = new HashSet<String>();

    static {
        properties.add("name");
        properties.add("sources");
    }

    protected Config() {
    }

    protected Config(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    @Override
    public Reference getReference() throws NamingException {
        Reference reference = new Reference(Config.class.getName(),ConfigObjectFactory.class.getName(),null);
        reference.add(new StringRefAddr("name",this.name));
        reference.add(new StringRefAddr("sources",this.sources));
        return reference;
    }

    public static boolean contains(String property){
        return properties.contains(property);
    }

}
