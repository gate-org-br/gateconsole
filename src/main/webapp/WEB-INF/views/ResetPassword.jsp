<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<body>
		<form action="ResetPassword" method="post">
			<div class="Login">
				<div>
					<div>
						<img src='Logo.svg'/>
						<label>${app.id}</label>
						<label>Vers�o ${version}</label>
					</div>

					<g-tab-control type="dummy">
						<a href='Gate' style="flex-basis: 25%">
							Fazer Logon<g:icon type="2000"/>
						</a>
						<a href='SetupPassword' style="flex-basis: 25%">
							Trocar Senha<g:icon type="passwd"/>
						</a>
						<a href='ResetPassword' style="flex-basis: 25%" data-selected="true">
							Recuperar Senha<g:icon type="2023"/>
						</a>
						<a href='CreateAccount' style="flex-basis: 25%">
							Criar Conta<g:icon type="gate.entity.User"/>
						</a>
						<div>

							<fieldset>
								<div>
									<p>
										Entre com o seu login ou email na caixa de texto
										abaixo e clique em concluir. Uma nova senha alat�ria
										ser� criada e enviada para seu endere�o de email. Voc�
										poder� ent�o permanecer com ela ou troc�-la por outra
										de sua escolha.
									</p>
								</div>
								<fieldset>
									<legend>
										Login ou e-mail
									</legend>
									<label>
										<span>
											<g:icon type="gate.entity.User"/>
											<input type='TEXT' required='required' name='user.username' maxlength='64' tabindex='1' title='Entre com o seu login.'/>
										</span>
									</label>
								</fieldset>
							</fieldset>
						</div>
					</g-tab-control>
					<g-coolbar>
						<button class="Commit" tabindex='2'>
							Concluir<g:icon type="commit"/>
						</button>
						<hr/>
						<a class="Cancel" href="Gate" tabindex='3'>
							Desistir<g:icon type="cancel"/>
						</a>
					</g-coolbar>
				</div>
			</div>

			<g:superuser>
				<g:if condition="${not empty exception}">
					<g:stacktrace popup="popup"
						      title="Erro de sistema"
						      exception="${exception}"/>
				</g:if>
			</g:superuser>

			<g:if condition="${not empty messages}">
				<script type='text/javascript'>
					<g:iterator source="${messages}" target="message">
					alert('${message}');
					</g:iterator>
				</script>
			</g:if>
		</form>
	</body>
</g:template>

