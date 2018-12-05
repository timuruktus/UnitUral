package trelico.ru.unitUral.di.modules;

import toothpick.config.Module;
import trelico.ru.unitUral.projects.ProjectsDataSource;
import trelico.ru.unitUral.repositories.ProjectsRepository;

public class ProjectsModule extends Module {


    public ProjectsModule() {
        bind(ProjectsRepository.class).toInstance(new ProjectsRepository());
        bind(ProjectsDataSource.class).toInstance(new ProjectsDataSource());


    }
}
