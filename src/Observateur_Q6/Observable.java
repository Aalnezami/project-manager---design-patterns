package Observateur_Q6;

public interface Observable {

	void attacher(Observateur o);

	void detacher(Observateur o);

	void notifierObservateurs();
}
