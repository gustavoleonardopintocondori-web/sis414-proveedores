package com.example.demov3.Services;

import com.example.demov3.Entities.Proveedor;
import com.example.demov3.Repositories.ProveedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {
    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public Proveedor crear(Proveedor datos) {
        Proveedor proveedor = new Proveedor();
        copiarDatos(datos, proveedor);
        return repository.save(proveedor);
    }

    public List<Proveedor> listar() { return repository.findAll(); }

    public Optional<Proveedor> buscarPorId(Long id) { return repository.findById(id); }

    public Optional<Proveedor> actualizar(Long id, Proveedor datos) {
        return repository.findById(id).map(proveedor -> {
            copiarDatos(datos, proveedor);
            return repository.save(proveedor);
        });
    }

    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) { return false; }
        repository.deleteById(id);
        return true;
    }

    private void copiarDatos(Proveedor origen, Proveedor destino) {
        destino.setNombre(origen.getNombre());
        destino.setEmpresa(origen.getEmpresa());
        destino.setEmail(origen.getEmail());
        destino.setTelefono(origen.getTelefono());
    }
}
