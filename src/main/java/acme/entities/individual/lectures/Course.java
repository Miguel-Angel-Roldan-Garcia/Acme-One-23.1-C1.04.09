
package acme.entities.individual.lectures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

    // Serialisation identifier -----------------------------------------------

    protected static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
    protected String code;

    @NotBlank
    @Length(max = 75)
    protected String title;

    @NotBlank
    @Length(max = 100)
    protected String _abstract;

    @NotNull
    @Min(0)
    protected Integer retailPrice;

    @URL
    protected String link;

    // Derived attributes -----------------------------------------------------

    /* TODO Derived attribute courseNature */

    // Relationships ----------------------------------------------------------

}