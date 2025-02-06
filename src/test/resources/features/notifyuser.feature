# language: pt

Funcionalidade: Notificar usuário quando a extração de frames falhar
  Como um sistema de processamento de vídeos
  Quero enviar uma notificação por e-mail quando a extração de frames falhar
  Para que o usuário esteja ciente do problema

  Cenário: Enviar notificação por e-mail ao usuário quando a extração falhar
    Dado um usuário com e-mail "email@test.com"
    Quando o sistema detectar falha na extração de frames
    Então um e-mail deve ser enviado ao usuário informando o erro

  Cenário: Falha ao enviar e-mail de notificação
    Dado um usuário com e-mail "email@test.com"
    Quando o sistema tentar enviar o e-mail e falhar
    Então uma exceção deve ser lançada

  Cenário: Ler o conteúdo HTML do e-mail de notificação
    Dado um arquivo HTML de notificação de erro
    Quando o conteúdo for lido como string
    Então o HTML lido deve corresponder ao esperado

  Cenário: Receber evento de falha na extração e enviar e-mail
    Dado um evento de falha de extração para o arquivo "firstVideo.mp4" e usuário "email@test.com"
    Quando o sistema receber a mensagem da fila
    Então um e-mail de falha deve ser enviado ao usuário
