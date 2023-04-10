/*
 * WorkerApplicationCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.individual.assistants.Tutorial;
import acme.entities.individual.assistants.TutorialSession;
import acme.entities.individual.lectures.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", String.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		String courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", String.class);
		course = this.repository.findOneCourseByCode(courseId);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		Assistant assistant;
		Course course;

		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		course = this.repository.findOneCourseByCode(super.getRequest().getData("courseId", String.class));

		object = new Tutorial();
		object.setTitle("");
		object.setAbstract$("");
		object.setGoals("");
		object.setCode("");
		object.setDraftMode(true);
		object.setCourse(course);
		object.setAssistant(assistant);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		super.bind(object, "code", "title", "abstract$", "goals", "draftMode");
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;
			existing = this.repository.findOneTutorialByCode(object.getCode());
			super.state(existing == null, "code", "assistant.tutorial.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<TutorialSession> tutorialSessions;
		Double estimatedTotalTime;

		tutorialSessions = this.repository.findManySessionsByTutorialId(object.getId());
		estimatedTotalTime = 0.;

		for (final TutorialSession ts : tutorialSessions)
			estimatedTotalTime += ts.getDurationInHours();

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "draftMode");
		tuple.put("courseCode", this.repository.findOneCourseByCode(super.getRequest().getData("courseId", String.class)).getCode());
		tuple.put("estimatedTotalTime", estimatedTotalTime);

		super.getResponse().setData(tuple);
	}

}
