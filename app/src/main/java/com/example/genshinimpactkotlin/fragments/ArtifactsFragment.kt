package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.adapters.ArtifactAdapter
import com.example.genshinimpactkotlin.entidades.*
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class ArtifactsFragment : Fragment(), SearchView.OnQueryTextListener {

    private val artifacts: ArrayList<ArtifactImageName> = arrayListOf()
    private var rvArtifacts: RecyclerView? = null
    private var artifactList: HashMap<String, Artifact> = hashMapOf()
    private var artifactImagesList: HashMap<String, ArtifactImage> = hashMapOf()
    private var language = ""
    private var searchView: SearchView? = null
    private var artifactAdapter: ArtifactAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        language = arguments?.getString("language").toString()
        return inflater.inflate(R.layout.fragment_artifacts, container, false)
    }

    /** Cuando "artifactImagesList" y "artifactList" no estén vacías significará que
     *  ambas querys han sido realizadas, con lo cual se puede proceder a ordenar "artifactList"
     *  e iniciar "fillArtifacts" que necesitará datos de ambas colecciones.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        rvArtifacts = view.findViewById(R.id.rvArtifacts)
        searchView = view.findViewById(R.id.searchViewArtifact)
        initListener()

        readData(object: FirebaseCallBack {
            override fun onCallback() {
                if (artifactImagesList.isNotEmpty() && artifactList.isNotEmpty()) {
                    val artifactListSorted: MutableMap<String, Artifact> = TreeMap(artifactList)
                    fillartifacts(artifactListSorted)
                }
            }
        }, mDatabase.getReference("Data/$language/artifacts"), mDatabase.getReference("Image/artifacts"))

    }

    /**
     * @param artifactListSorted MutableMap ordenado por <String>
     * Llena "artifacts" e inicia el método initRecycler
     */
    private fun fillartifacts(artifactListSorted: MutableMap<String, Artifact>) {
        artifactListSorted.keys.forEach {
            artifacts.add(
                ArtifactImageName(
                    it,
                    artifactList[it]?.name,
                    artifactList[it]?.`1pc`,
                    artifactList[it]?.`2pc`,
                    artifactList[it]?.`4pc`,
                    artifactList[it]?.rarity,
                    when(artifactImagesList[it]?.flower.isNullOrBlank()) {
                        true -> artifactImagesList[it]?.circlet
                        false -> artifactImagesList[it]?.flower
                    }
                )
            )
        }
        initRecycler()
    }

    /**
     * Llena rvartifacts (RecyclerView) con su correspondiente adaptador y modificaciones
     */
    private fun initRecycler() {
        artifactAdapter = ArtifactAdapter(artifacts)

        rvArtifacts?.adapter = artifactAdapter
        val columns = (((context?.resources?.displayMetrics?.widthPixels))?.div(480))
        rvArtifacts?.layoutManager = columns?.let { GridLayoutManager(context, it) }
    }

    /**
     * Este método se utiliza de forma auxiliar para utilizar la interface "FirebaseCallBack"
     * que se llama al final de cada query.
     * Dentro se realizan 2 Querys independientes a la base de datos
     */
    private fun readData(firebaseCallBack: FirebaseCallBack, refartifacts: DatabaseReference, refartifactImage: DatabaseReference) {
        refartifacts.keepSynced(true)
        refartifacts.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    artifactList[it.key.toString()] = it.getValue(Artifact::class.java)!!
                }
                firebaseCallBack.onCallback()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        refartifacts.keepSynced(true)
        refartifactImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    artifactImagesList[it.key.toString()] = it.getValue(ArtifactImage::class.java)!!

                }
                firebaseCallBack.onCallback()

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun initListener() {
        searchView?.setOnQueryTextListener(this)
    }

    /**
     * @param query
     * Cada vez que el texto del "SearchView" se envía se ejecuta filtrando por query:String
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        artifactAdapter?.filterName(query!!)
        return false
    }

    /**
     * @param query
     * Cada vez que el texto del "SearchView" cambia se ejecuta  filtrando por query:String
     */
    override fun onQueryTextChange(query: String?): Boolean {
        artifactAdapter?.filterName(query!!)
        return false
    }
}
