<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:path/>
		</legend>
		<label>
			Nome:
			<span>
				<g:label property='form.name'/>
			</span>
		</label>
	</fieldset>

	<g-coolbar>
		<g:link action='Update' _form.id="${screen.form.id}" tabindex="2"/>
		<g:link data-confirm="Tem certeza de que deseja remover este registro?"
			action="Delete" _form.id="${screen.form.id}" tabindex="2"/>
		<hr/>
		<g:hide tabindex="2">
			Retornar<g:icon type="return"/>
		</g:hide>
	</g-coolbar>

	<g-tab-control>
		<g:link screen="Auth" _form.func.id="${screen.form.id}"/>
		<g:link screen="FuncScreen$User" _func.id="${screen.form.id}"/>
		<g:link screen="FuncScreen$Role" _func.id="${screen.form.id}"/>
	</g-tab-control>
</g:template>