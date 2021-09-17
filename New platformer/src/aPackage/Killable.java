package aPackage;
/**
 * This interface is used to mark that a certain object can be killed
 * @author Kiya.Zadeh22
 *
 */
public interface Killable {
	
	public abstract void kill();
	public abstract void cleanup();
}
