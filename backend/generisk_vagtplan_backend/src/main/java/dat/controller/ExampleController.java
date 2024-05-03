package dat.controller;

import dat.dao.ExampleDAO;
import dat.dto.ExampleDTO;
import dat.model.ExampleEntity;
import io.javalin.http.Context;

public class ExampleController extends Controller<ExampleEntity, ExampleDTO> {

    private final ExampleDAO dao;

    public ExampleController(ExampleDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void helloWorld(Context ctx) {
        ctx.status(200);
        ctx.result("Hello world!");
    }
}