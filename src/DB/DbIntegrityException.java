
package DB;

/**
 *
 * @author Francisco Mendonça
 */
public class DbIntegrityException extends RuntimeException{
    
    public DbIntegrityException(String msg) {
	super(msg);
    }
}
