package aPackage;
/**
 * This interface is used to mark that a certain object can be killed
 * @author Kiya.Zadeh22
 *
 */
public interface Killable {
	/**
	 * This method is ussed to determine how this Killable can be killed
	 */
	public abstract void kill();
	/**
	 * Should be used to do cleanup for this Killable after it is killed
	 */
	public abstract void cleanup();
}
