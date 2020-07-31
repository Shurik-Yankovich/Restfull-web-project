module Senla {
    opens bookstore.controller to InitAnnotation;
    opens bookstore.controller.action to InitAnnotation;
    opens bookstore.view.in to InitAnnotation;
    opens bookstore.view.out to InitAnnotation;
    opens bookstore.repository.file to InitAnnotation;
    opens bookstore.repository.list to InitAnnotation;
    opens bookstore.repository.base to InitAnnotation;
    opens bookstore.service.request to InitAnnotation;
    opens bookstore.service.order to InitAnnotation;
    opens bookstore.service.storage to InitAnnotation;
    opens bookstore.util.csv to InitAnnotation;
    requires InitAnnotation;
}