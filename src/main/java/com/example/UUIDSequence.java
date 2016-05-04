package com.example;

import java.util.Vector;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.internal.databaseaccess.Accessor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sequencing.Sequence;
import org.eclipse.persistence.sessions.Session;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDSequence extends Sequence implements SessionCustomizer {

    private static final long serialVersionUID = -8111815193483132234L;

    private static final TimeBasedGenerator UUID_GENERATOR = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    private static final UUIDPersistenceConverter CONVERTER = new UUIDPersistenceConverter();

    public UUIDSequence() {
        super();
    }

    public UUIDSequence(String name) {
        super(name);
    }

    @Override
    public Object getGeneratedValue(Accessor accessor, AbstractSession writeSession, String seqName) {
        return CONVERTER.convertToDatabaseColumn(UUID_GENERATOR.generate());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Vector getGeneratedVector(Accessor accessor, AbstractSession writeSession, String seqName, int size) {
        return null; // NOSONAR - this method is unused anyway.
    }

    @Override
    public void onConnect() { // nothing to do
    }

    @Override
    public void onDisconnect() { // nothing to do
    }

    @Override
    public boolean shouldAcquireValueAfterInsert() {
        return false;
    }

    @Override
    public boolean shouldUseTransaction() {
        return false;
    }

    @Override
    public boolean shouldUsePreallocation() {
        return false;
    }

    @Override
    public void customize(Session session) throws Exception {
        UUIDSequence sequence = new UUIDSequence("uuid-sequence");
        session.getLogin().addSequence(sequence);
    }

}
