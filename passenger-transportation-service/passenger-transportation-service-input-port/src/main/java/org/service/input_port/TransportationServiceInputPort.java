package org.service.input_port;

import org.service.entity.*;

import java.util.List;

public interface TransportationServiceInputPort {




    GraphEntity findAll();



    GraphEntity findGraphByIds(List<String> ids);

}
