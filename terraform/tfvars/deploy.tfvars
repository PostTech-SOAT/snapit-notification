##############################################################################
#                      GENERAL                                               #
##############################################################################

application = "snapit-notification"
aws_region  = "us-east-1"

##############################################################################
#                      HELM                                                  #
##############################################################################

ingress_nginx_name = "ingress-nginx-controller"

helm_service_template = [{
  name                = "snapit-notification"
  namespaces          = "snapit"
  is_there_config_map = true
  is_there_secret     = true
  secret_type         = "Opaque"

  helm_chart_key_value = {
    "chartName"                                     = "snapit-notification"
    "serviceAccount.create"                         = "true"
    "serviceAccount.name"                           = "snapit-notification-svc-acc"
    "service.type"                                  = "NodePort"
    "service.port"                                  = "8080"
    "service.targetPort"                            = "8080"
    "ingress.enabled"                               = "true"
    "image.pullPolicy"                              = "Always"
    "resources.requests.cpu"                        = "100m"
    "resources.requests.memory"                     = "256Mi"
    "resources.limits.cpu"                          = "200m"
    "resources.limits.memory"                       = "512Mi"
    "livenessProbe.initialDelaySeconds"             = "300"
    "livenessProbe.periodSeconds"                   = "90"
    "livenessProbe.timeoutSeconds"                  = "30"
    "readinessProbe.initialDelaySeconds"            = "90"
    "readinessProbe.periodSeconds"                  = "90"
    "readinessProbe.timeoutSeconds"                 = "30"
    "autoscaling.enabled"                           = "true"
    "autoscaling.minReplicas"                       = "1"
    "autoscaling.maxReplicas"                       = "1"
    "autoscaling.targetCPUUtilizationPercentage"    = "70"
    "autoscaling.targetMemoryUtilizationPercentage" = "70"
    "configMap.enabled"                             = "false"
    "nameOverride"                                  = "snapit-notification"
    "fullnameOverride"                              = "snapit-notification-api"
  }

  helm_chart_config_map = {
    "APPLICATION_NAME" = "snapit-notification"
    "API_DOCS_PATH"    = "/api-docs"
    "API_PORT"         = "8080"
    "DATABASE_URL"     = "jdbc:postgresql://snapit-db.cq23vjwswp3a.us-east-1.rds.amazonaws.com:5432/postgres"
  }


}]
