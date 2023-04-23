/**
 */
package petrinet;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link petrinet.Place#getNbToken <em>Nb Token</em>}</li>
 * </ul>
 *
 * @see petrinet.PetrinetPackage#getPlace()
 * @model
 * @generated
 */
public interface Place extends Node {
	/**
	 * Returns the value of the '<em><b>Nb Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nb Token</em>' attribute.
	 * @see #setNbToken(int)
	 * @see petrinet.PetrinetPackage#getPlace_NbToken()
	 * @model required="true"
	 * @generated
	 */
	int getNbToken();

	/**
	 * Sets the value of the '{@link petrinet.Place#getNbToken <em>Nb Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nb Token</em>' attribute.
	 * @see #getNbToken()
	 * @generated
	 */
	void setNbToken(int value);

} // Place
