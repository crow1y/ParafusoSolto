# Feira Parafuso Solto — Feira de Invenções Malucas

App Android em **Java** com 3 Activities: **Feira → Ficha → Câmara de Testes**.

## Como abrir
1. Descompacte o ZIP.
2. Android Studio > **File > Open** > pasta `ParafusoSolto` (a que tem `settings.gradle`).
3. Espere o Gradle Sync e clique em **Run ▶**.
4. APK: **Shift Shift > Generate APKs** > **locate** > `app-debug.apk`.

## Arquivos
| Arquivo | O que faz |
|---|---|
| `Invencao.java` | Modelo (nome, inventor, categoria, descrição, imagem) |
| `Extras.java` | Constantes das chaves dos extras e a TAG do Logcat |
| `InvencaoAdapter.java` / `InvencaoViewHolder.java` | RecyclerView com clique e clique longo |
| `MainActivity.java` | Feira: cadastro, lista/grade, remoção, `onNewIntent()` |
| `FichaActivity.java` | Ficha: valida extras, animação XML, abre a câmara |
| `TesteActivity.java` | Câmara: resultado, selo com `.animate()`, CLEAR_TOP |
| `res/anim/entrada_ficha.xml` | Animação de entrada (fade + translate + scale, 500 ms) |

## Onde está cada requisito
- **Spinner** com categorias em `strings.xml` (posição 0 = "Selecione a categoria")
- **5 invenções iniciais**; as cadastradas pelo usuário usam `img_invencao_padrao`
- `notifyItemInserted()` + `scrollToPosition()` ao cadastrar
- Clique longo → diálogo de confirmação → `notifyItemRemoved()`
- Botão **Ver em grade / Ver em lista**: `GridLayoutManager(2)` ↔ `LinearLayoutManager`
- Ficha valida os extras; se algum for inválido: Toast + `finish()`
- Selo: `animate().alpha().scaleX().scaleY()` em 600 ms
- Encerrar: `FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP` → `onNewIntent()` mostra "Último teste: invenção – resultado"
- `Log.d()` em onCreate/onResume/onStop/onDestroy das 3 telas (tag **CicloFeira**)
- `onResume()` da feira atualiza "Protótipos na feira: N"
- Animações entre 400 e 600 ms

## Logcat
Na aba **Logcat**, digite no filtro: `tag:CicloFeira`

Ao **Encerrar avaliação** aparece algo assim (a ordem pode variar um pouco):
```
MainActivity: onNewIntent
TesteActivity: onDestroy   (removida pelo CLEAR_TOP)
FichaActivity: onDestroy   (removida pelo CLEAR_TOP)
MainActivity: onResume
```
Repare que **não aparece** um novo `MainActivity: onCreate`: a tela inicial foi reaproveitada.

## Roteiro do vídeo (~3 min)
1. **Logcat:** deixe a aba aberta com o filtro `tag:CicloFeira` e mostre o `onCreate` e o `onResume` da MainActivity.
2. **Validação:** toque em Cadastrar vazio (erro), nome "Ab" (erro), sem categoria (aviso).
3. **Cadastro:** "Chapéu Despertador", inventor "Igor Crow", categoria "Tecnologia Inútil". A lista rola até o novo item com a imagem padrão e o contador sobe para 6.
4. **Lista/grade:** toque em **Ver em grade** e depois em **Ver em lista**.
5. **Remoção:** segure um item, confirme e mostre o contador diminuindo.
6. **Ficha:** toque em "Patins a Vapor" e mostre a animação de entrada.
7. **Botão Voltar** do celular: volta para a feira. Abra a ficha de novo.
8. **Câmara:** toque em **Testar protótipo**, depois em **Rever ficha** (volta para a ficha) e em **Testar** de novo.
9. **Teste:** marque "Aprovada", escreva uma observação e toque em **Confirmar** para ver o selo animado.
10. **Encerrar avaliação:** volta para a feira com "Último teste: Patins a Vapor – Aprovada".
11. Mostre no Logcat o `onNewIntent` e os `onDestroy` da Ficha e da Câmara, **sem** um novo `onCreate` da MainActivity.
12. Aperte **Voltar** na feira: o app fecha, o que prova que não ficou nenhuma tela extra na pilha.
