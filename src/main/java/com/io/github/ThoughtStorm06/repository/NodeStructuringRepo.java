package com.io.github.ThoughtStorm06.repository;
import com.io.github.ThoughtStorm06.model.GraphStruct;
import com.io.github.ThoughtStorm06.model.Node;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

@Repository
public class NodeStructuringRepo extends json_crud {
    @Value("${pathweaver.data-folder}")
    private String  datafolderpath;

    
}
