package exceptions;

import cache.factory.CacheType;

public class IdTypeException extends RuntimeException{

private static final long serialVersionUID = 1123424234423L;

private final static String error = ". The id is not of appropriate type! For USER it must be string, for others Integer";;
	
	public IdTypeException(CacheType type, Object id) {
		super("For " + type + " id was " + id.getClass().getSimpleName() + error);
	}
	
}
