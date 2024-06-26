package dev.matheuscruz.kafkamoon.api.presentation.controller;

import dev.matheuscruz.kafkamoon.api.application.usecases.cluster.get.GetClusterInfoUseCase;
import dev.matheuscruz.kafkamoon.api.application.usecases.cluster.get.GetClusterInfoUseCaseOutput;
import dev.matheuscruz.kafkamoon.api.application.usecases.cluster.nodes.list.ListNodesUseCase;
import dev.matheuscruz.kafkamoon.api.application.usecases.cluster.nodes.list.ListNodesUseCaseOutput;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/api/v1/cluster")
public class ClusterController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClusterController.class);
  private final GetClusterInfoUseCase getClusterInfoUseCase;
  private final ListNodesUseCase listNodesUseCase;

  public ClusterController(
      final GetClusterInfoUseCase getClusterInfoUseCase, ListNodesUseCase listNodesUseCase) {
    this.getClusterInfoUseCase = getClusterInfoUseCase;
    this.listNodesUseCase = listNodesUseCase;
  }

  @PreAuthorize("hasRole('READER')")
  @GetMapping("/info")
  @Timed("get_cluster_info_time")
  public ResponseEntity<?> getCluster() {
    LOGGER.info("[flow:cluster.get.info] Getting cluster info");
    GetClusterInfoUseCaseOutput output = this.getClusterInfoUseCase.execute();
    return ResponseEntity.ok(output);
  }

  @PreAuthorize("hasRole('READER')")
  @GetMapping("/nodes")
  @Timed("get_nodes_time")
  public ResponseEntity<?> getNodes() {
    LOGGER.info("[flow:cluster.get.nodes] Getting nodes from Kafka cluster");
    List<ListNodesUseCaseOutput> output = this.listNodesUseCase.execute();
    return ResponseEntity.ok(output);
  }
}
