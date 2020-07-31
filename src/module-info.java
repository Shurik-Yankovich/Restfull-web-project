module Senla {
    requires PropertiesAnnotation;
    opens bookstore.service.storage to PropertiesAnnotation, InitAnnotation;
    requires InitAnnotation;
}