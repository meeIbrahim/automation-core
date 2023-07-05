package resource.data.readers;


import resource.cached.ZTAParameters;

import resource.data.UnknownResource;

public interface reverseLookup <T extends ZTAParameters> {
    T using(String Parent,String identifier) throws UnknownResource;

    default T using(ZTAParameters parameters) throws UnknownResource {
        return using(parameters.parent,parameters.identifier);
    };

}
