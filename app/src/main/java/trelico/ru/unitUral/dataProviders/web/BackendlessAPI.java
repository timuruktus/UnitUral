package trelico.ru.unitUral.dataProviders.web;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trelico.ru.unitUral.models.modelObjects.Project;

public interface BackendlessAPI{


    //    https://api.backendless.com/<application-id>/<api-key>/<operation-specific-path>
    //    https://api.backendless.com/BE03913B-C957-F065-FF44-EDD595A65700/A72F8F29-A888-762B-FF98-F4B9676F4200/services/api1/get-all-projects
    String APP_ID = "BE03913B-C957-F065-FF44-EDD595A65700";
    String API_KEY = "1D4579C4-334D-3EC8-FF5B-500CD540D500";
    String BASE_URL = "https://api.backendless.com/" + APP_ID + "/" + API_KEY + "/";
    String API_URL = "services/api1/";
    //    String IMAGES_FOLDER = "memes";
//    String IMAGES_URL = BASE_URL + "files/" + IMAGES_FOLDER + "/";
    int DEFAULT_PAGE_SIZE = 20;

    @GET(API_URL + "get-projects")
    Call<List<Project>> getProjects(@Query("offset") int offset,
                                    @Query("count") int count);

    @GET(API_URL + "get-project")
    Call<Project> getProject(String projectId, String userId);

    @GET(API_URL + "get-project-users")
    Call<Project> getProjectUsers(String projectId);


}



