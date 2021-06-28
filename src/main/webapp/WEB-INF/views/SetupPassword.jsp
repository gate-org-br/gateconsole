<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<body>
		<form action="SetupPassword" method="post">
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
						<a href='SetupPassword' style="flex-basis: 25%" data-selected="true">
							Trocar Senha<g:icon type="passwd"/>
						</a>
						<a href='ResetPassword' style="flex-basis: 25%">
							Recuperar Senha<g:icon type="2023"/>
						</a>
						<a href='CreateAccount' style="flex-basis: 25%">
							Criar Conta<g:icon type="gate.entity.User"/>
						</a>
						<div>
							<fieldset>
								<label data-size="8">
									Login:
									<span>
										<g:icon type="gate.entity.User"/>
										<input type='TEXT' required='required' name='user.userID' maxlength='64' tabindex='1' title='Entre com o seu login.'/>
									</span>
								</label>
								<label data-size="8">
									Senha atual:
									<span>
										<g:icon type="passwd"/>
										<input type='PASSWORD' required='required' name='user.passwd' maxlength='64' tabindex='1' title='Entre com a sua senha atual.'/>
									</span>
								</label>
								<label  data-size="8">
									Nova senha:
									<span>
										<g:icon type="2057"/>
										<input type='PASSWORD' required='required' name='user.change' maxlength='64' tabindex='1' title='Entre com a sua nova senha.'/>
									</span>
								</label>
								<label  data-size="8">
									Repita a nova senha:
									<span>
										<g:icon type="2058"/>
										<input type='PASSWORD' required='required' name='user.repeat' maxlength='64' tabindex='1' title='Repita sua nova senha.'/>
									</span>
								</label>
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

