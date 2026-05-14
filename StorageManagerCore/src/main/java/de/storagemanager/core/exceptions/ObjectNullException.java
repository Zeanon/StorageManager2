package de.storagemanager.core.exceptions;

/**
 * Indicates that a passed object has been null where it NotNull is required
 *
 * @author Zeanon
 */
@SuppressWarnings("unused")
public final class ObjectNullException extends NullPointerException {

	private static final long serialVersionUID = 7960773302491731978L;

	public ObjectNullException() {
		super();
	}

	public ObjectNullException(final String message) {
		super(message);
	}
}