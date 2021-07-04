<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>


<g:template filename="/WEB-INF/views/FULL.jsp">
	<form id='form' method='POST' action='#'>
		<fieldset>
			<legend>
				<g:path/>
			</legend>

			<label data-size='4'>
				Nome:
				<span>
					<g:text property='form.name' tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='4'>
				Login:
				<span>
					<g:text property='form.userID' tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='8'>
				Perfil
				<span>
					<g:hidden id='form.role.id' property="form.role.id" required=''/>
					<g:text id='form.role.name' property='form.role.name' required='' data-getter="adsfasdf"/>
					<g:link id="adsfasdf" module="#" screen="Role" action="Search" data-get='form.role.id, form.role.name'
						arguments="form.name=@{form.role.name}"
						tabindex='1' title='Selecionar Perfil'>
						<g:icon type="search"/>
					</g:link>
				</span>
			</label>
			<label data-size='2'>
				Ativo:
				<span>
					<g:select property="form.active" tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='2'>
				CPF:
				<span>
					<g:text property='form.CPF' tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='2'>
				Data de Nascimento:
				<span>
					<g:text class='Date'
						property='form.birthdate' tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='2'>
				Sexo:
				<span>
					<g:select property='form.sex' tabindex='1' required=''/>
				</span>
			</label>
			<label data-size='2'>
				Telefone:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:text property='form.phone' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Celular:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:text property='form.cellPhone' tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				E-Mail:
				<span>
					<g:icon type="2034"/>
					<g:text property='form.email' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<g-coolbar>
			<g:link class="Action" method="post" tabindex='2'>
				Pesquisar<g:icon type="search"/>
			</g:link>
			<g:link target="_report" method="post" action="Report" tabindex='2'/>
			<g:link target='_stack' action='Upload' tabindex='2' data-on-hide="reload"/>
			<g:link target='_stack' action='Insert' tabindex='2' data-on-hide="reload"/>
		</g-coolbar>

		<g:block condition="${screen.POST}"
			 otherwise="Entre com os critérios de busca e clique em Pesquisar">
			<g:table class="c1 c5" data-collapse="Phone"
				 condition="${not empty screen.page}"
				 otherwise="Nenhum registro encontrado">
				<g:caption value="USUÁRIOS: ${screen.page.paginator.dataSize}"/>

				<g:thead>
					<g:tr>
						<g:th style="width: 60px" ordenate="active" value="Ativo"/>
						<g:th ordenate="name" value="Nome"/>
						<g:th ordenate="userID" value="Login"/>
						<g:th ordenate="role.name" value="Perfil"/>
						<g:th style="width: 120px" ordenate="registration" value="Cadastro"/>
					</g:tr>
				</g:thead>
				<g:tfoot>
					<g:tr>
						<g:td colspan="5">
							<g:paginator/>
						</g:td>
					</g:tr>
				</g:tfoot>
				<g:tbody source="${screen.page}">
					<g:tr target='_stack' action='Select' _form.id='${target.id}' data-on-hide="reload">
						<g:td title="Ativo" value="${target.active}"/>
						<g:td title="Nome" value="${target.name}"/>
						<g:td title="Login" value="${target.userID}"/>
						<g:td title="Perfil" value="${target.role.name}"/>
						<g:td title="Cadastro" value="${target.registration}"/>
					</g:tr>
				</g:tbody>
			</g:table>
		</g:block>
	</form>
</g:template>

