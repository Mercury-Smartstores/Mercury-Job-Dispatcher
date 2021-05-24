package metadata;

public abstract class MetadataReader<T extends MetadataObject> {

    public abstract T read(String metadata) throws Exception;

}
