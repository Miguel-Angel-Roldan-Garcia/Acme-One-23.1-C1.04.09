
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.lectures.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureListService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("courseId", int.class);
		
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Boolean status;
		
		status = super.getRequest().getPrincipal().hasRole("acme.roles.Lecturer");
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CourseLecture> object;
		if(super.getRequest().hasData("courseId")) {
			int courseId = super.getRequest().getData("courseId",int.class);
			object = this.repository.findManyCourseLecturesByCourseId(courseId);
		}else {	
			int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
			object = this.repository.findManyCourseLecturesByLecturerId(lecturerId);
		}

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple ;
		tuple = super.unbind(object,"id");
		tuple.put("editable",object.getCourse().isDraftMode());
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("courseTitle", object.getCourse().getTitle());
		tuple.put("lectureCode", object.getLecture().getCode());
		tuple.put("lectureTitle", object.getLecture().getTitle());
		tuple.put("lectureNature", object.getLecture().getNature());
		tuple.put("lecturePublished", object.getLecture().isDraftMode()?"":"x");
		
		super.getResponse().setData(tuple);
	}

}
