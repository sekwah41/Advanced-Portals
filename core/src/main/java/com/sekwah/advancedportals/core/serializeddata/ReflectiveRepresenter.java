package com.sekwah.advancedportals.core.serializeddata;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

public class ReflectiveRepresenter extends Representer {

    public ReflectiveRepresenter(DumperOptions options) {
        super(options);
        this.getPropertyUtils().setBeanAccess(BeanAccess.FIELD);
    }
}
