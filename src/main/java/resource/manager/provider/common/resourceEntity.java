package resource.manager.provider.common;

import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.ResourceException;
import resource.data.UsedResource;

public interface resourceEntity {

    ZTAParameters getFree(String Reference) throws UsedResource, ResourceException; // Get Resource with no attached objects
    ZTAParameters create(String Reference) throws UsedResource, ResourceException;
    ZTAParameters getActive(String Reference) throws UsedResource, ResourceException; // Get Resource that is active
    ZTAParameters get(String Reference) throws cachedNotFound; // Get Resource Parameters from Cache

    void delete(String Reference); // Delete Resource
    void removeAssociations(String Reference); // Detach all associated child objects






}
