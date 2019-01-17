package trelico.ru.unitUral.utils;

import trelico.ru.unitUral.models.modelObjects.CustomResponse;

public interface BaseRepository {

    CustomResponse getDataList(int offset, int count);

}
