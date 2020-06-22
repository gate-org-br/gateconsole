<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<figure>
		<g:with name="photo" value="${screen.photo}">
			<g:if condition="${not empty photo}">
				<div>
					<img src='${photo}'/>
				</div>
			</g:if>
		</g:with>
		<fieldset style="margin: 0">
			<legend>
				<g:name type="${action}"/>
				<g:icon type="${action}"/>
			</legend>
			<label data-size='2'>
				Ativo:
				<span>
					<g:label property='form.active'/>
				</span>
			</label>
			<label data-size='2'>
				Cadastro:
				<span>
					<g:label property='form.registration'/>
				</span>
			</label>
			<label data-size='2'>
				Login:
				<span>
					<g:label property='form.userID'/>
				</span>
			</label>
			<label data-size='2'>
				Senha:
				<span>
					<label>Resetar</label>
					<g:shortcut module="#" screen="#" action="Passwd" arguments="form.id=${screen.form.id}"/>
				</span>
			</label>
			<label data-size='4'>
				Nome:
				<span>
					<g:label property='form.name'/>
				</span>
			</label>
			<label data-size='4'>
				Perfil:
				<span>
					<label>
						<g:print value="${screen.form.role.name}" empty="N/A"/>
					</label>
					<g:if condition="${not empty screen.form.role.id}">
						<g:shortcut module="#" screen="Role" action="Select"
							    arguments="form.id=${screen.form.role.id}"/>
					</g:if>
				</span>
			</label>
			<label data-size='2'>
				CPF:
				<span>
					<g:label property='form.CPF'/>
				</span>
			</label>
			<label data-size='2'>
				Nascimento:
				<span>
					<g:icon type="gate.type.Date"/>
					<g:label property='form.birthdate'/>
				</span>
			</label>
			<label data-size='4'>
				Sexo:
				<span>
					<g:label property='form.sex'/>
				</span>
			</label>
			<label data-size='2'>
				Telefone:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:label property='form.phone'/>
				</span>
			</label>
			<label data-size='2'>
				Celular:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:label property='form.cellPhone'/>
				</span>
			</label>
			<label data-size='4'>
				E-Mail:
				<span>
					<g:icon type="2034"/>
					<g:label property='form.email'/>
				</span>
			</label>
			<label>
				Detalhes:
				<span style='flex-basis: 80px'>
					<g:label property='form.details'/>
				</span>
			</label>
		</fieldset>
	</figure>

	<g-coolbar>
		<g:link module='#' screen='#' action='Update' arguments="form.id=${screen.form.id}"/>
		<g:link module='#' screen='#' action="Delete" arguments="form.id=${screen.form.id}"
			style='color: #660000' data-confirm="Tem certeza de que deseja remover este registro?"/>
		<hr/>
		<a href="#" target="_hide">
			Retornar<g:icon type="return"/>
		</a>
	</g-coolbar>

	<g-tab-control>
		<g:link module="#" screen="Auth" arguments="form.user.id=${screen.form.id}"/>
		<g:link module="#" screen="UserScreen$Func" arguments="user.id=${screen.form.id}"/>
	</g-tab-control>
</g:template>