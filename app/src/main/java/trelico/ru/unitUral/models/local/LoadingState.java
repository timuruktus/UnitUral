package trelico.ru.unitUral.models.local;

public enum LoadingState {

    LOADING,
    LOADING_INITIAL,
    ERROR_FROM_WEB,
    ERROR_INITIAL_FROM_WEB,
    ERROR_FROM_LOCAL,
    ERROR_INITIAL_FROM_LOCAL,
    FINISHED_INITIAL,
    FINISHED
}
