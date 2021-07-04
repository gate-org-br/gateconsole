<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<g:login module="gateconsole.modules.admin">
		<div class="Login">
			<div>
				<div>
					<img src='Logo.svg'/>
					<label>${app.id}</label>
					<label>Versão ${version}</label>
				</div>

				<g-tab-control type="dummy">
					<a href='Gate' data-selected="true" style="flex-basis: 25%">
						Fazer Logon<g:icon type="2000"/>
					</a>
					<a href='SetupPassword' style="flex-basis: 25%">
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
							<label>
								Login:
								<span>
									<g:icon type="gate.entity.User"/>
									<input type="text" required='required' name='$userid' maxlength='64' tabindex="1" title='Entre com o seu login.'/>
								</span>
							</label>
							<label>
								Senha:
								<span>
									<g:icon type="passwd"/>
									<input type="password" required='required' name='$passwd' maxlength='32' tabindex="1" title='Entre com a sua senha.'/>
								</span>
							</label>
						</fieldset>
					</div>
				</g-tab-control>

				<g-coolbar>
					<button class="Commit" tabindex="2">
						Concluir<g:icon type="commit"/>
					</button>
				</g-coolbar>
			</div>
		</div>

		<g:superuser>
			<g:if condition="${not empty exception}">
				<g:stacktrace title="Erro de sistema"
					      exception="${exception}"/>
			</g:if>
		</g:superuser>

		<g:if condition="${not empty messages}">
			<script type='text/javascript'>
				<g:iterator source="${messages}" target="message">
						    alert('${message}');
				</g:iterator						>
			</script						>
		</g:if						>
	</g:login>
</g:template>