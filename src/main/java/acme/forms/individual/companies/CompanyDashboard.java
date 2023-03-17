
package acme.forms.individual.companies;

import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	protected Integer[]			practicumCountByMonth;

	// Session duration per practicum

	protected Statistic			practicumInPractica;

	protected Statistic			practicumSessionInPractica;

}
