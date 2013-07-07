package ch.ethz.e4mooc.client;

/**
 * Generic View Interface which all views have to implement.
 * @author hce
 *
 * @param <T> the type of the presenter interface which belongs to a view
 */
public interface IView <T extends IPresenter> {
	
	/**
	 * Sets the presenter for a corresponding view.
	 * @param presenter the presenter
	 */
	public void setPresenter(T presenter);
}
