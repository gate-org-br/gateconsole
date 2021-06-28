<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<article>
		<section>
			<fieldset>
				<legend>
					<g:path/>
				</legend>
				<fieldset>
					<g:table condition="${not empty screen.page}"
						 otherwise="Nenhum registro encontrado">
						<g:caption value="FUNÇÕES: ${screen.page.size()}"/>
						<g:thead>
							<g:tr>
								<g:td>
									<input data-filter type="text"/>
								</g:td>
							</g:tr>
							<g:tr>
								<g:th data-sortable="data-sortable" value="Nome"/>
							</g:tr>
						</g:thead>
						<g:tbody source="${screen.page}">
							<g:tr target='_stack' action="Select" _form.id="${target.id}" data-on-hide="reload">
								<g:td  value="${target.name}"/>
							</g:tr>
						</g:tbody>
					</g:table>
				</fieldset>
			</fieldset>
		</section>

		<footer>
			<g-coolbar>
				<g:link class="Action" target='_stack' action='Insert' data-on-hide="reload" tabindex='3'/>
			</g-coolbar>
		</footer>
	</article>
</g:template>