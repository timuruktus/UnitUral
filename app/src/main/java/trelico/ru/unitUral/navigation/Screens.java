package trelico.ru.unitUral.navigation;

import androidx.fragment.app.Fragment;
import trelico.ru.unitUral.projects.ProjectsFragment;

public class Screens {


    public static final class ProjectsScreen extends SupportAppScreen {

        // We can put anything that we want onto ProjectScreen constructor

        @Override
        public Fragment getFragment() {
            return ProjectsFragment.newInstance();
        }
    }
}
